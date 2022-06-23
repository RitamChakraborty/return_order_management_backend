FROM ritamchakraborty/return_order_component_processing:1.0
ARG CONFIG_SERVER_URL=https://config-server-ritam.herokuapp.com
ARG DB_USERNAME=ritam
ARG DB_PASSWORD=password
ARG PACKAGING_AND_DELIVERY_API_URL=https://packaging-and-delivery-ritam.herokuapp.com