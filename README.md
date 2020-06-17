# SPEC

## Requirements
 - query by hash string (shortened), then api return origin URL. 
 - query by URL, then api return hash string (shortened)
 - query by URL with user defined shorten hash string
 - hash-url mapping have limited life time.

## API
### GET /api/v1/UrlHash/${hash string}
query by hash string (shortened), then api return origin URL.  
#### Request
Hash string should have length between 4 - 16  
Hash String should only contains characters matched the rex rule: [-_a-zA-Z0-9]  

#### Response
case 1: Find shortened URL by the hash string  
status code : 200  
example response body:  
```
{
    "url": "https://www.google.com/",
    "urlHash": "f_7838"
}
```

case 2: Find no correlated (alive) URL   
status code : 404  
example response body:  
```
{
    "timestamp": "2020-06-17T15:13:13.554+00:00",
    "status": 404,
    "error": "Not Found",
    "message": "",
    "path": "/api/v1/UrlHash/asdasdasdasdsad"
}
```

### POST /api/v1/UrlHash/
query by URL, then api return hash string (shortened)  
query by URL with user defined shorten hash string  

#### Request
POST body should be JSON format with speicified keys.  
KEYS:  
 - url[necessary]: url string with length less or equal to 2048  
 - urlHash[optional]: hash tring matched the above requirments.   


#### Response
case 1: Query URL and have shortend hash string  
status code : 200  
example response body:  
```
{
    "url": "https://www.google.com/",
    "urlHash": "f_7838"
}
```

case 2: Query URL with valid user defined hash string  
status code : 200  
example response body:  
```
{
    "url": "https://www.google.com/",
    "urlHash": "userDefined""
}
```

case 3: Query URL with invalid user defined hash string  
status code : 400  
```
{
    "timestamp": "2020-06-17T15:36:10.804+00:00",
    "status": 400,
    "error": "Bad Request",
    "message": "",
    "path": "/api/v1/UrlHash/"
}
```

case 4: Query different URL with existed user defined hash string  
status code : 400  
```
{
    "timestamp": "2020-06-17T15:36:10.804+00:00",
    "status": 400,
    "error": "Bad Request",
    "message": "",
    "path": "/api/v1/UrlHash/"
}
```
