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

## 🔥 Auth Service Architecture
![Auth Service Architecture](/images/Auth%20Server%20Architecture.png)
The image "Auth-Server-Architecture.jpg" visualizes the complete authentication flow:

1. **User Action**: User sends email and password from client (Browser/Postman) to the Auth Server.

2. **Auth Server**: Validates credentials, issues a JWT on success.

3. **JWT Handling**: JWT goes back to the client (browser/Postman). For protected resources, client passes the JWT to a resource server.

4. **Resource Server**: Validates JWT either locally or by consulting the Auth Server. Upon successful validation, a response is served back to the client.

### Key Points:

* The flow is stateless—no session maintained on servers, only JWT.

* The JWT represents identity and permissions securely.

* Resource servers rely on JWT validation to control access.

## 🌍 OAuth Architecture
![OAuth Architecture](/images/OAuth%20Architecture.png)
The image demonstrates how federated login works (example: Google OAuth):

* **User logs in with Google**: The login page redirects to Google Auth Server for credential verification.

* **Tokens Issued**: After verification, Google provides a one-time auth token to the resource server.

* **Access Control**: The resource server passes the token to the user, who can use it to access protected resources.

## 💡 Security Tips
* Always use HTTPS in production.

* Ensure JWT token cookies are HttpOnly, Secure, and SameSite=Strict.

* Configure appropriate token expiry and support refresh tokens.

