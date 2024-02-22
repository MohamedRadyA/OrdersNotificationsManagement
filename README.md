# Orders and Notifications Management

Our Order and Notification Management System, built with Java Spring Boot and adhering to RESTful API standards, design patterns, and SOLID principles, offers a suite of features designed to enhance the shopping and order management experience. Here's what you can expect:

## Features

- **Product Catalog:** Access a detailed list of all products, including information like serial number, name, category, vendor, price, and stock levels.

- **Customer Accounts:** Easily create accounts with customizable balance options for hassle-free online shopping.

- **Order Flexibility:** Place orders for yourself or as gifts for friends with the option to combine products in a single transaction.

- **Transparent Fees:** Get detailed breakdowns of service and shipping fees with every order for clear and straightforward pricing.

- **Order Tracking:** Stay updated on your order's progress, from placement through to delivery, with comprehensive status tracking.

- **Notification System:** Receive timely updates about your order, including confirmations, payment receipts, and shipping notifications.

- **Custom Notifications:** Enjoy personalized notification messages tailored to different scenarios and preferences, ensuring relevant and engaging communication.

--- 

## Installation

To install and run this module, it's recommended to have the following prerequisites:

- Java 11 or higher
- Maven 3.6 or higher
- Postman or any other API testing tool

You also need to clone the GitHub repository and configure the application properties file with your database credentials and other settings.

* **Clone the repo**
    ```bash
    git clone https://github.com/KarimH537/OrdersNotificationsManagement.git
    ```
* **Change directory**
    ```bash
    cd OrdersNotificationsManagement\orders
    ```
* **Build the project**
    ```bash
    mvn clean install
    ```
* **Run the application**
    ```bash
    mvn spring-boot:run
    ```
---

## Usage

To use this module, you need to test the API endpoints with Postman or any other tool. The module exposes the following API endpoints, grouped by category:

### Inventory

- `/inventory/getall`: This endpoint returns a list of all products available for purchase, with serial number, name, category, sub-category, price, and remaining items count. It accepts a GET request with no parameters.

- `/inventory/get/{serialNo}`: This endpoint returns the information of a specific product, such as name, category, serial number, vendor, price, and the quantity. It accepts a GET request with a path variable containing the serial number of the product. For example:

    ```http
    GET /inventory/get/P001
    ```

- `/inventory/add`: This endpoint allows adding a new product to the inventory, with name, category, sub-category, price, and items count. It accepts a POST request with a JSON body containing the product details. For example:

    ```JSON
    {
      "serialNumber":"1",
      "name": "Camera",
      "vendor": "Cannon",
      "category": "Electronics",
      "price": 300,
      "quantity": 5
    }
    ```

-  `/inventory/increase`: This endpoint allows increasing the quantity of an existing item in the inventory. It accepts a PUT request with a JSON body containing the item's serial number and the quantity to be added. For example:

    ```JSON
    {
      "serialNumber":"1",
      "quantity": 5
    }
    ```

- `/inventory/getcategories`: This endpoint allows retrieving all the categories of items in the inventory. It accepts a GET request and does not require any parameters or a request body. For example, a simple GET request to this endpoint could look like this:
    
    ```
    GET /inventory/getcategories
    ```
    
### User
- `/user/register`: This endpoint allows creating a new user account, with name, password, and balance. It accepts a POST request with a JSON body containing the user details. For example:

    ```JSON
    {
      "name": "John Doe",
      "password": "123456",
      "balance": 1000
    }
    ```

- `/user/login`: This endpoint allows an existing user to log in to their account. It accepts a POST request with a JSON body containing the user’s name and password. For example:
    
    ```JSON
    {
      "name": "John Doe",
      "password": "123456"
    }
    ```

- `/user/setchannels/{username}`: This endpoint allows selecting the channels to receive the notifications, such as email, or SMS. It accepts a PUT request with a JSON body containing the user prefrences. For example:
- 
    ```JSON
    {
    "email":true,
    "sms":true
    }
    ```

### Order
- `/order/create`: This endpoint allows creating a new order, with the username and address.

    ```JSON
    {
    "username":"John Doe",
    "address":"123 Main St"
    }
    ```

- `/order/addproduct`: This endpoint allows adding a product to an existing order. It accepts a POST request with a JSON body containing the order ID, the serial number, and the quantity. For example:

    ```JSON
    {
      "id":1,
      "serialNumber":"1",
      "quantity":2
    }
    ```

- `/order/addorder`: This endpoint allows adding an order to another order, creating a compound order. It accepts a POST request with a JSON body containing the parent order ID and the child order ID. For example:
- 
    ```JSON
    {
      "parentOrderId": "0001",
      "childOrderId": "0002"
    }
    ```
    
- `/order/get/{id}`: This endpoint returns the details of an order, such as the products, the recipients, the fees, and the status. It accepts a GET request with a path variable containing the order ID. For example:
 
    ```
    GET /order/get/0001
    ```

- `/order/cancelplacement`: This endpoint allows canceling the placement of an order, before it is shipped. It accepts a PUT request with a JSON body containing the order ID. 

- `/order/cancelshipping`: This endpoint allows canceling the shipping of an order, after it is shipped but before it is delivered. It accepts a PUT request with a JSON body containing the order ID. 

- `/order/place`: This endpoint allows placing an order, confirming the payment and the shipping. It accepts a PUT request with a JSON body containing the order ID.

- `/order/ship`: This endpoint allows shipping an order, updating the status and sending a notification. It accepts a PUT request with a JSON body containing the order ID. 

    For example:
    ```JSON
    {
      "orderId": "0001"
    }
    ```

### Info
- `/inventory/getstats`: This endpoint returns the statistics of the module, such as the number of users, products, orders, notifications, etc. It accepts a GET request with no parameters.

---

# Contributors

* [Abdelrhman Gamal](https://github.com/Gamal72)
* [Hazem Waheed](https://github.com/HazemWaheed2050)
* [Karim Hassib](https://github.com/KarimH537)
* [Mohamed Rady](https://github.com/MohamedRadyA)



