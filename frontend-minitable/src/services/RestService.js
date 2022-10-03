import axios from "axios";

class RestService {
    calculateCells(data) {
        return axios.post('http://localhost:8080/cells', data)
    }
}

export default new RestService()