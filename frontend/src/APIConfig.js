let APIConfig = {}

APIConfig.gatewayUrl = ((localStorage.getItem("toykiwi_gateway_url") === null) ? "http://localhost:8088" : localStorage.getItem("toykiwi_gateway_url"))
APIConfig.videoUrl = `${APIConfig.gatewayUrl}/api/video`
APIConfig.collectedDataUrl = `${APIConfig.gatewayUrl}/api/collectedData`
APIConfig.externalSystem = `${APIConfig.gatewayUrl}/api/externalSystem`

console.log("current API Config: ", APIConfig)
export default APIConfig;
