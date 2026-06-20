$url = "https://archive.apache.org/dist/maven/maven-3/3.8.8/binaries/apache-maven-3.8.8-bin.zip"
$output = "apache-maven-3.8.8-bin.zip"
$extractPath = "."

Write-Host "Downloading Maven 3.8.8..."
Invoke-WebRequest -Uri $url -OutFile $output
Write-Host "Download completed!"

Write-Host "Extracting Maven..."
Expand-Archive -Path $output -DestinationPath $extractPath -Force
Write-Host "Extraction completed!"

Write-Host "Maven has been downloaded and extracted to: $extractPath\apache-maven-3.8.8"
