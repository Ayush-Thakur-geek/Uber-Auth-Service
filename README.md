# Auth Server
Welcome to the Auth Server Service — a secure, modern authentication microservice implementing token-based 
authentication with JWTs. This service provides robust user authentication for web, mobile, and microservice-based 
resource servers, perfectly suited for scalable and stateless applications.

## 🌟Features
* **JWT Token-based Authentication**: Stateless, secure, and scalable.
* **Password Security**: Passwords are hashed using BCrypt.
* **RESTful Endpoints**: Easy integration with browsers, mobile apps, and API clients.
* **Support for HTTP-only Cookies**: Secure token delivery, resistant to XSS attacks.
* **Plug-and-Play**: Easily connect your resource servers, microservices, or client apps.

## 🗝️ Types of Authentication
![Types of Authentications mechanism](/images/Types%20of%20Auths.png)
In the image "Types-of-Auths.jpg," you’ll see a conceptual overview of authentication methods:

* **Token-based Auth**: Stateless, relies on tokens (e.g., JWTs) for user verification. Tokens are often stored in HTTP-only cookies for increased security, making them inaccessible to JavaScript and thus resistant to XSS attacks.

* **Session-based Auth**: Server maintains session state; the client holds a session ID.

* **Hybrid Auth**: Combines features of both for advanced workflows.

**Highlighted Concept:**

`"We store the token using 'Http Only Cookie', as information stored in this cookie cannot be accessed by JavaScript, 
i.e., the token won't be accessible on the client side."
Why? This design prevents client-side scripts from stealing authentication tokens, protecting users against many XSS 
exploits.`