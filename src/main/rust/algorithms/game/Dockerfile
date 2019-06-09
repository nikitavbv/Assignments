FROM frolvlad/alpine-glibc

EXPOSE 8080

WORKDIR /app
COPY Rocket.toml /app/Rocket.toml
COPY static /app/static
COPY target/release/game /app/game

ENTRYPOINT [ "/app/game" ]