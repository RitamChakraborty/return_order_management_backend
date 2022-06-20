FROM ritamchakraborty/return_order_config_server:1.0
ENTRYPOINT ["java","-Xmx512M","-Dserver.port=$PORT","-jar","app.jar"]