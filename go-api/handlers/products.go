package handlers

import (
	"encoding/json"
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
	"github.com/redis/go-redis/v9"
	"go.mongodb.org/mongo-driver/v2/bson"
	"go.mongodb.org/mongo-driver/v2/mongo"
)

type ProductHandler struct {
	Coll  *mongo.Collection
	Redis *redis.Client
}

func NewProductHandler(coll *mongo.Collection, rdb *redis.Client) *ProductHandler {
	return &ProductHandler{Coll: coll,
		Redis: rdb}
}

type IdProduct struct {
	ID string `uri:"id" binding:"required"`
}

func (h *ProductHandler) GetByID(ctx *gin.Context) {
	var reqId IdCustomer
	if err := ctx.ShouldBindUri(&reqId); err != nil {
		ctx.JSON(http.StatusBadRequest, gin.H{"err": "err in param"})
		return
	}

	cacheKey := "product:" + reqId.ID
	cached, err := h.Redis.Get(ctx, cacheKey).Result()

	if err == nil {
		var result bson.M
		if err := json.Unmarshal([]byte(cached), &result); err == nil {
			ctx.JSON(http.StatusOK, result)
			return
		}
	}

	var result bson.M
	id, _ := bson.ObjectIDFromHex(reqId.ID)

	filter := bson.M{"_id": id}
	err = h.Coll.FindOne(ctx, filter).Decode(&result)
	if err == mongo.ErrNoDocuments {
		ctx.JSON(http.StatusNotFound, gin.H{"err": "product not found"})
		return
	}

	if err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{"err": "database error"})
		return
	}

	data, err := json.Marshal(result)
	if err == nil {
		h.Redis.Set(ctx, cacheKey, data, 10*time.Minute)
	}

	ctx.JSON(http.StatusOK, result)
}
