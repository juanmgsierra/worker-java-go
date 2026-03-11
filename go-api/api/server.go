package api

import (
	"github.com/gin-gonic/gin"
	"github.com/juanmgsierra/go-api/db"
	"github.com/juanmgsierra/go-api/handlers"
	"github.com/redis/go-redis/v9"
)

type Server struct {
	router          *gin.Engine
	customerHandler *handlers.CustomerHandler
	productHandler  *handlers.ProductHandler
}

func NewServer(collections *db.Collections, redis *redis.Client) *Server {
	server := &Server{
		customerHandler: handlers.NewCustomerHandler(collections.Customers, redis),
		productHandler:  handlers.NewProductHandler(collections.Products, redis),
	}

	router := gin.Default()
	v1 := router.Group("/api/v1")

	customers := v1.Group("/customers")
	customers.GET("/:id", server.customerHandler.GetByID)

	products := v1.Group("/products")
	products.GET("/:id", server.productHandler.GetByID)

	server.router = router
	return server
}

func (server *Server) Start(address string) error {
	return server.router.Run(address)
}
