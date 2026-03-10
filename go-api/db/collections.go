package db

import "go.mongodb.org/mongo-driver/v2/mongo"

type Collections struct {
	Customers *mongo.Collection
	Products  *mongo.Collection
}
