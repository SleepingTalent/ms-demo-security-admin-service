MODE 1000,1000

start "security-admin-service-1" java -jar @executable.server.jar.name@.jar --server.port=2222 --spring.application.name=security-admin-service --spring.profiles.active=dev
