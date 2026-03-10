package handlers

import (
	"net/http"

	"github.com/gin-gonic/gin"
	"go.mongodb.org/mongo-driver/v2/bson"
	"go.mongodb.org/mongo-driver/v2/mongo"
)

type ProductHandler struct {
	Coll *mongo.Collection
}

func NewProductHandler(coll *mongo.Collection) *ProductHandler {
	return &ProductHandler{Coll: coll}
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

	var result bson.M
	id, _ := bson.ObjectIDFromHex(reqId.ID)

	filter := bson.M{"_id": id}
	err := h.Coll.FindOne(ctx, filter).Decode(&result)
	if err == mongo.ErrNoDocuments {
		ctx.JSON(http.StatusNotFound, gin.H{"err": "product not found"})
		return
	}

	ctx.JSON(http.StatusOK, result)
}
