import axios from 'axios';

const API_URL = 'http://54.180.118.216:8080';
const data = axios
  .get(`${API_URL}/api/cafe`)
  .then(function (response) {
    // 성공 핸들링
    console.log(response);
  })
  .catch(function (error) {
    // 에러 핸들링
    console.log(error);
  })
  .finally(function () {
    // 항상 실행되는 영역
  });

console.log(data);
