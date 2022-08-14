const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    host: 'localhost',
    port: 8081,
    proxy: {
      '/' : {
        target: 'http://localhost:8080',
        changeOrigin: true,
        ws: false
      }
    }
  }
})
