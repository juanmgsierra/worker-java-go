db = db.getSiblingDB("company");

db.createCollection("customers");
db.createCollection("products");
db.createCollection("orders");
db.createCollection("orderErrors");

db.customers.insertMany([
  { _id: ObjectId("69b0df7ccfb808f94a8563b1"), name: "Juan Garcia", isActive: true },
  { _id: ObjectId("69b0df7ccfb808f94a8563b2"), name: "Jose Lopez", isActive: false },
  { name: "Armando Rodriguez", isActive: true }
]);

db.products.insertMany([
  { _id: ObjectId("69b0df7ccfb808f94a8563b4"), name: "Macbook air", price: 1200 },
  { _id: ObjectId("69b0df7ccfb808f94a8563b5"), name: "Mouse", price: 25 },
  { name: "Iphone 17", price: 1000 },
  { name: "Apple watch", price: 500 },
  { name: "Iphone case", price: 30 },
  { name: "Airpods", price: 259 }
]);