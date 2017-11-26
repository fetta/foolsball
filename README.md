# foolsball
How to run:
- mvn clean install
- docker-compose up --build 
After that you can run TeamServiceApp class in your IDE (it requires a running database)

Roadmap:
- TeamService should be finished ASAP - PlayerController is done, next in line: TeamController
- team-service should be dockerized and ran with other docker containers
- Gateway container should be integrated
- Next services (Match Service, Statistics Service)

Backlog:
- Database initialisation? (for now hibernate create-drop is enough)
