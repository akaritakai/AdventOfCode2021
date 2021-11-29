FROM gradle:7.3.0-jdk17

WORKDIR "/opt/aoc"

COPY . .

ENTRYPOINT ["gradle", "run"]