$url = "https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.1.0/maven-wrapper-3.1.0.jar"
$output = ".mvn\wrapper\maven-wrapper.jar"

Write-Host "Downloading maven-wrapper.jar..."
Invoke-WebRequest -Uri $url -OutFile $output
Write-Host "Download completed!"
