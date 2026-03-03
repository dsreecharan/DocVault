@Bean
public RestTemplate restTemplate() {
    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
    factory.setConnectTimeout(5000);   // 5 seconds connect
    factory.setReadTimeout(60000);     // 60 seconds response (LLMs can be slow)
    return new RestTemplate(factory);
}