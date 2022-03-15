_: run

run: build
	bash -c "docker run -d -p 80:8080 -e JWT_SECRET=$(openssl rand -hex 16) sceneit:backend"

build:
	docker build . -t sceneit:backend