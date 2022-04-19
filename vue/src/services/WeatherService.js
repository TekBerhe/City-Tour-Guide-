import axios from "axios";

export default {
    getCurrent() {
    let lat = '29.951066';
	let lon = '-90.071532';
	let key = 'daa4b4037bf1c45508665f6ec5ad9451';
	let lang = 'en';
	let units = 'us';
	let url = `http://api.openweathermap.org/data/2.5/onecall?lat=${lat}&lon=${lon}&appid=${key}&units=${units}&lang=${lang}`;
    
    return axios.get(url);

    }
}