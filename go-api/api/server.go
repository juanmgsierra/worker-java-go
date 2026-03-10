package api

import (
	"github.com/gin-gonic/gin"
	"github.com/juanmgsierra/go-api/db"
	"github.com/juanmgsierra/go-api/handlers"
)

type Server struct {
	router          *gin.Engine
	customerHandler *handlers.CustomerHandler
	productHandler  *handlers.ProductHandler
}

func NewServer(collections *db.Collections) *Server {
	server := &Server{
		customerHandler: handlers.NewCustomerHandler(collections.Customers),
		productHandler:  handlers.NewProductHandler(collections.Products),
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
