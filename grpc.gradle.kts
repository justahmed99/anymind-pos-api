protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.7.1"
    }
    plugins {
        grpc {
            artifact = "io.grpc:protoc-gen-grpc-java:1.19.0"
        }
    }
    generateProtoTasks {
        ofSourceSet('main').each { task ->
            task.builtins {
                java {
                    outputSubDir = 'proto_gen'
                }
            }
            task.plugins {
                grpc {
                    outputSubDir = 'proto_gen'
                }
            }
        }
    }
    generatedFilesBaseDir = "$projectDir/src/"
}

task cleanProtoGen {
    doFirst {
        delete("$projectDir/src/main/proto_gen")
    }
}

clean.dependsOn cleanProtoGen

        sourceSets {
            main {
                java {
                    srcDirs += 'src/main/proto_gen'
                }
            }
        }