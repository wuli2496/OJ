const express = require('express');
const routes = require('./routes');

const app = express();
app.use('/', routes);

app.listen(8088, () => {
    console.log('服务已启动 http://localhost:8088')
})

