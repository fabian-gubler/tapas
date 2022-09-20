# tapas-fs22
The master repository for the TAPAS application to be developed in Fall Semester 2022.

## Project Structure
This project is structured as follows:
* [app](app): a very simple web service developed using Spring Boot, based on the tutorial at https://spring.io/guides/gs/spring-boot/
* [docker-compose.yml](docker-compose.yml): Docker Compose configuration file for all services
* [docker-compose-local.yml](docker-compose-local.yml): Docker Compose configuration file to run all services on local Docker
* [.github/workflows/build-and-deploy.yml](.github/workflows/build-and-deploy.yml): GitHub actions script (CI/CD workflow)

In the following weeks we will add more services to this project and show you how integrate them.

### How to Run Your Service Locally
You can run and test your micro-service on your local machine just like a regular Maven project:
* Run from IntelliJ:
    * Reload *pom.xml* if necessary
    * Run the micro-service's main class from IntelliJ for all required projects
* Use Maven to run from the command line:
```shell
mvn spring-boot:run
```
* Run with local Docker:
```shell
docker compose -f docker-compose-local.yml up --build
```

## How to Deploy on your VM
1. Start your Ubuntu VM on Switch.
    * VM shuts down automatically at 2 AM
    * Group admins can do this via https://engines.switch.ch/horizon
2. Push new code to the *main* branch
    * Check the status of the workflow on the *Actions* page of the GitHub project
    * We recommend to test your project locally before pushing the code to GitHub.
3. Open in your browser `https://app.${PUB_IP}.asse.scs.unisg.ch`

For the server IP address (see below), you should use dashes instead of dots, e.g.: `127.0.0.1` becomes `127-0-0-1`.

## VM Configurations

Specs (we can upgrade if needed):
* 1 CPU
* 2 GB RAM
* 20 GB HD
* Ubuntu 22.04

| Name | Server IP |
|-------|-----------|
|SCS-ASSE-VM-Group1|86.119.35.40|
|SCS-ASSE-VM-Group2|86.119.35.213|
|SCS-ASSE-VM-Group3|86.119.34.242|
|SCS-ASSE-VM-Group4|86.119.35.199|
|SCS-ASSE-VM-Group5|86.119.35.72|

## Architecture Decision Records
We recommend to use [adr-tools](https://github.com/npryce/adr-tools) to manage your ADRs here in
this GitHub project in a dedicated folder. The tool works best on a Mac OS or Linux machine.
