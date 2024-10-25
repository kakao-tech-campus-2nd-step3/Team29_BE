FROM gradle:jdk21-jammy

WORKDIR /app

CMD ["gradle", "bootRun"]