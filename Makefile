#include .env

ifndef PROJECT_NAME
	PROJECT_NAME=safziy-mp
endif


default: local

.PHONY: update-git
update-git:
	@echo "========= git pull ========="
	@git pull origin master

.PHONY: build
build:
	@echo "========= local build ========="
	@sh ./gradlew build

.PHONY: run
run:
	@echo "========= local run ========="
	@sh ./gradlew bootRun

.PHONY: build-image
build-image:
	@echo "========= build docker image ========="
	@docker build -t safziy-all .

.PHONY: docker-run
docker-run:
	@echo "========= run docker image ========="
	@docker ps | grep safziy-all && docker stop safziy-all
	@docker ps -a | grep safziy-all && docker rm safziy-all
	@docker run -p 8080:8080 -d -e spring.profiles.active=prod --name safziy-all safziy-all

local: build run

prod: update-git build build-image docker-run
