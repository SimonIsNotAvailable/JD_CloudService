FROM node:alpine
WORKDIR /usr/app/front
EXPOSE 3000
COPY ./ ./
RUN npm install
CMD ["npm", "start"]

FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8099
ARG JAR_FILE=build/libs/rebounder-chain-backend-0.0.2.jar
ADD ${JAR_FILE} rebounder-chain-backend.jar
ENTRYPOINT ["java","-jar","/rebounder-chain-backend.jar"]