# Contract testing jvm pact example

> A consumer-driven contract testing example using pact-jvm, spring boot, and maven to verify the interactions between microservices.

Project that contains :

- `provider`: example provider
- `consumer`: example consumer
- `consumer-ui`: example consumer

## Intro to Pact

<p align="center">
    <img src="documentation/pact-logo.png" alt="Pact Logo" align="center">
</p>

[Pact](https://docs.pact.io) is a consumer-driven contract testing tool. 
This means the contract is written as part of the consumer tests. 
A major advantage of this pattern is that only parts of the communication 
that are actually used by the consumer(s) get tested. 
This in turn means that any provider behaviour not used by current consumers 
is free to change without breaking tests.

Pact enables consumer driven contract testing, 
providing a mock service and DSL for the consumer project, 
interaction playback and verification for the service provider project.

<p align="center">
    <img src="documentation/explication.png" alt="Pact Explication" align="center">
</p>

The Pact family of testing frameworks provide support for Consumer Driven Contract Testing between dependent systems 
where the integration is based on HTTP (or message queues for some of the implementations).