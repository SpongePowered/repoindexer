RepoIndexer 
=============
## Prerequisites
* [Java] 7

## Purpose
this tool generates a simple download page for maven repos using a mustache template.

## Clone
The following steps will ensure your project is cloned properly.  
1. `git clone git@github.com:SpongePowered/repoindexer.git`  
2. `cd repoindexer`  
3. `cp scripts/pre-commit .git/hooks`

## Building
__Note:__ If you do not have [Gradle] installed then use ./gradlew for Unix systems or Git Bash and gradlew.bat for Windows systems in place of any 'gradle' command.

In order to build repoindexer you simply need to run the `gradle` command. You can find the compiled JAR file in `./build/libs` labeled similarly to 'repoindexer-x.x.x-SNAPSHOT.jar'.

## Contributing
Are you a talented programmer looking to contribute some code? We'd love the help!
* Open a pull request with your changes, following our [guidelines](CONTRIBUTING.md).
* Please follow the above guidelines and requirements for your pull request(s) to be accepted.

[Eclipse]: http://www.eclipse.org/
[Gradle]: http://www.gradle.org/
[Homepage]: http://spongepowered.org/
[IntelliJ]: http://www.jetbrains.com/idea/
[Java]: http://java.oracle.com/
[MIT License]: http://www.tldrlegal.com/license/mit-license
[Community Chat]: https://webchat.esper.net/?channels=sponge
[Development Chat]: https://webchat.esper.net/?channels=spongedev
[Preparing for Development]: https://docs.spongepowered.org/en/preparing/
