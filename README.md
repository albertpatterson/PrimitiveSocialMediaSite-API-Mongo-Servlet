# PrimitiveSocialMediaSite-API-Mongo-Servlet
This project will feature an API for the Primitive Social Media Site project. This version of the API will be written in Java using servlets to provide access to the resources, using the the indicated methods in the list below. The compiled front end from the PrimitiveSocialMediaSite-Angular-Front-End repo will also be included
- [ ] API
  - [x] Personal Information
    - [x] post: create a new user (sign up)
    - [x] get: get a user's (or multiple users') information
    - [x] put: update user information
    - [x] delete: delete a user (delete account)
  - [x] Member Authentication
    - [x] post: create a new session (sign in)
    - [x] get: get the current session (check credentials)
    - [x] delete: delete the current session (sign out)
  -[x] Sign up
    - [x] post: create a new user
  - [x] Messages
    - [x] post: create a new message
    - [x] get: get messages
    - [x] delete: delete a message
  - [x] Posts
    - [x] post: create a new post
    - [x] get: get posts
    - [x] delete: delete a post
  - [x] premium (manage premium content)
    - [x] post: create a new premium item
    - [x] get: get premium items
    - [x] delete: delete a premium item
  - [ ] Services
    - [x] DatabaseService
      - [ ] Mock
      - [ ] Mongodb
- [x] View (compiled front end from the PrimitiveSocialMediaSite-Angular-Front-End rep)
- [ ] Tests
  - [ ] Services
  - [ ] Servlets

## Prerequisits
* Java
* Web server (Tomcat)
* npm

Installation
```bash
# Clone this repository
$ git clone https://github.com/albertpatterson/PrimitiveSocialMediaSite-API-Mongo-Servlet.git

# Go into the "web" repository
$ cd PrimitiveSocialMediaSite-API-Mongo-Servlet/web

# Install dependencies
$ npm install
```
