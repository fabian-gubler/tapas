# tapas-tasks

Micro-service for Managing Tasks in a Task List implemented following Hexagonal Architecture.

Based on examples from book "Get Your Hands Dirty on Clean Architecture" by Tom Hombergs

Technologies: Java, Spring Boot, Maven

**Note:** this repository contains an [EditorConfig](https://editorconfig.org/) file (`.editorconfig`)
with default editor settings. EditorConfig is supported out-of-the-box by the IntelliJ IDE. To help maintain
consistent code styles, we recommend to reuse this editor configuration file in all your services.

## HTTP API Overview
The code we provide includes a minimalistic uniform HTTP API for (i) creating a new task, (ii) retrieving
a representation of the current state of a task, and (iii) patching the representation of a task, which
is mapped to an integration event.

The representations exchanged with the API use two media types:
* a JSON-based format for task with the media type `application/task+json`; this media type is defined
  in the context of our project, but could be [registered with IANA](https://www.iana.org/assignments/media-types)
  to promote interoperability (see
  [TaskJsonRepresentation](src/main/java/ch/unisg/tapastasks/tasks/adapter/in/formats/TaskJsonRepresentation.java)
  for more details)
* the [JSON Patch](http://jsonpatch.com/) format with the registered media type `application/json-patch+json`, which is also a
  JSON-based format (see sample HTTP requests below).

For further developing and working with your HTTP API, we recommend to use [Postman](https://www.postman.com/).

### Creating a new task

A new task is created via an `HTTP POST` request to the `/tasks/` endpoint. The body of the request
must include a representation of the task to be created using the content type `application/task+json`
defined in the context of this project. A valid representation must include at least two required fields
(see [TaskJsonRepresentation](src/main/java/ch/unisg/tapastasks/tasks/adapter/in/formats/TaskJsonRepresentation.java)
for more details):
* `taskName`: a string that represents the name of the task to be created
* `taskType`: a string that represents the type of the task to be created

A sample HTTP request with `curl`:
```shell
curl -i --location --request POST 'http://localhost:8081/tasks/' \
--header 'Content-Type: application/task+json' \
--data-raw '{
  "taskName" : "task1",
  "taskType" : "computation",
  "originalTaskUri" : "http://example.org",
  "inputData" : "1+1"
}'

HTTP/1.1 201
Location: http://localhost:8081/tasks/cef2fa9d-367b-4e7f-bf06-3b1fea35f354
Content-Length: 0
Date: Sun, 17 Oct 2021 21:03:34 GMT

```

If the task is created successfully, a `201 Created` status code is returned together with a
`Location` header field that points to the URI of the newly created task.

### Retrieving a task

The representation of a task is retrieved via an `HTTP GET` request to the URI of task.

A sample HTTP request with `curl`:
```shell
curl -i --location --request GET 'http://localhost:8081/tasks/cef2fa9d-367b-4e7f-bf06-3b1fea35f354'

HTTP/1.1 200
Content-Type: application/task+json
Content-Length: 170
Date: Sun, 17 Oct 2021 21:07:04 GMT

{
  "taskId":"cef2fa9d-367b-4e7f-bf06-3b1fea35f354",
  "taskName":"task1",
  "taskType":"computation",
  "taskStatus":"OPEN",
  "originalTaskUri":"http://example.org",
  "inputData":"1+1"
}
```

### Patching a task

REST emphasizes the generality of interfaces to promote uniform interaction. For instance, we can use
the `HTTP PATCH` method to implement fine-grained updates to the representational state of a task, which
may translate to various integration events. However, to conform to the uniform interface
constraint in REST, any such updates have to rely on standard knowledge — and thus to hide away the
implementation details of our service.

In addition to the `application/task+json` media type we defined for our uniform HTTP API, a standard
representation format we can use to specify fine-grained updates to the representation of tasks
is [JSON Patch](http://jsonpatch.com/). In what follow, we provide a few examples of `HTTP PATCH` requests.
For further details on the JSON Patch format, see also [RFC 6902](https://datatracker.ietf.org/doc/html/rfc6902)).

#### Changing the status of a task to EXECUTED

Sample HTTP request that changes the status of the task to `EXECUTED` and adds an output result:

```shell
curl -i --location --request PATCH 'http://localhost:8081/tasks/cef2fa9d-367b-4e7f-bf06-3b1fea35f354' \
--header 'Content-Type: application/json-patch+json' \
--data-raw '[ {"op" : "replace", "path": "/taskStatus", "value" : "EXECUTED" },
  {"op" : "add", "path": "/outputData", "value" : "2" } ]'

HTTP/1.1 200
Location: http://localhost:8081/tasks/cef2fa9d-367b-4e7f-bf06-3b1fea35f354
Content-Length: 0
Date: Sun, 17 Oct 2021 21:32:25 GMT

```

Internally, this request is mapped to a
[TaskExecutedEvent](src/main/java/ch/unisg/tapastasks/tasks/application/port/in/TaskExecutedEvent.java).
The HTTP response returns a `200 OK` status code together with location of the resource.
