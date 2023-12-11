let APIConfig = {}

APIConfig.gatewayUrl = "http://localhost:8088"
APIConfig.videoUrl = `${APIConfig.gatewayUrl}/api/video/`
APIConfig.collectedDataUrl = `${APIConfig.gatewayUrl}/api/collectedData/`

export default APIConfig;
