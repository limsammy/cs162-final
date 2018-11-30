# README

This is the official documentation for Recommendify (name still undecided), my project for CS162. In this application we utilize an already-written Java wrapper for the Spotify HTTP api (found [here](https://github.com/thelinmichael/spotify-web-api-java)) for our authentication, JavaFX for our frontend, and Java's built-in Properties class for maintaining state. The prime directive of this application has yet to be written.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Support](#support)
- [Contributing](#contributing)

## Prerequisites

- Apache Maven v3.6.0
- JDK v1.8.0_191
- Premium Spotify account

## Installation

Install a copy locally and navigate to project root directory:
```sh
git clone git@github.com:limsammy/cs162-final.git && cd cs162-final
```

Use Maven to build dependencies and compile production jar:
```sh
mvn clean compile jfx:package
```

Jar will be compiled to:
```sh
target/jfx/app/recommendify-1.0-SNAPSHOT.jar
```

## Usage

After compiling the .jar, navigate to the release directory:
```sh
cd target
```

Launch the .jar file:
```sh
java -jar recommendify-1.0-SNAPSHOT.jar
```

## Support

Please [open an issue](https://github.com/limsammy/cs162-final/issues) for support.

## Contributing

Please contribute using the [Github Work Flow](https://guides.github.com/introduction/flow/). Create a feature branch, add commits, and then [open a pull request](https://github.com/limsammy/cs162-final/compare/).

For ideas on features to write, please check out our [Github Projects](https://github.com/limsammy/cs162-final/projects/1) page which is currently tracking our progress.
