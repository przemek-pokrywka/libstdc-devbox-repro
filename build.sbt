
val toolkitV = "0.2.0"
val toolkit = "org.scala-lang" %% "toolkit" % toolkitV
val toolkitTest = "org.scala-lang" %% "toolkit-test" % toolkitV

ThisBuild / scalaVersion := "3.3.0"
libraryDependencies += toolkit
libraryDependencies += (toolkitTest % Test)

libraryDependencies +=
"com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"

PB.deleteTargetDirectory := true

PB.protocExecutable := (
if (protocbridge.SystemDetector.detectedClassifier() == "osx-aarch_64") {
// to change if needed, this is where protobuf manual compilation put it for me
// brew install protobuf
file("/opt/homebrew/bin/protoc")
} else PB.protocExecutable.value
)

Compile / PB.targets := Seq(
scalapb.gen(grpc = false, singleLineToProtoString = true, lenses = false) ->
// (sourceManaged in Compile).value
// Workaround for IDEA issue with managed sources https://youtrack.jetbrains.com/issue/SCL-17177
baseDirectory.value / "scala-proto"
)

Compile / unmanagedSourceDirectories += baseDirectory.value / "scala-proto"

