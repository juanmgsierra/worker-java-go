package main

import (
	"context"
	"fmt"
	"log"
	"os"

	"github.com/joho/godotenv"
	"github.com/juanmgsierra/go-api/api"
	"github.com/juanmgsierra/go-api/db"
	"go.mongodb.org/mongo-driver/v2/mongo"
	"go.mongodb.org/mongo-driver/v2/mongo/options"
)

func main() {
	err := godotenv.Load()

	if err != nil {
		fmt.Println("Error loading .env file")
	}

	uriDb := os.Getenv("MONGODB_URI")

	if uriDb == "" {
		log.Fatal("Set your 'MONGODB_URI' environment variable. ")
	}

	client, err := mongo.Connect(options.Client().ApplyURI(uriDb))
	if err != nil {
		panic(err)
	}
	defer func() {
		if err := client.Disconnect(context.TODO()); err != nil {
			panic(err)
		}
	}()

	database := client.Database("company")

	collections := &db.Collections{
		Customers: database.Collection("customers"),
		Products:  database.Collection("products"),
	}

	server := api.NewServer(collections)
	serverAddress := os.Getenv("SERVER_ADDRESS")
	err = server.Start(serverAddress)
	if err != nil {
		log.Fatal("cannot start server: ", err)
	}
}
