
class Service {
    searchCityCountry(city, country) {
        if(country===""){
            return fetch("http://localhost:8080/api/location/city?city="+city, {
                method: 'GET',
                mode: 'cors',
            })
        }
        return fetch("http://localhost:8080/api/location/city_country?city="+city+"&country="+country, {
            method: 'GET',
            mode: 'cors',
        })
    }
    forecastData(lat, lon) {
        return fetch("http://localhost:8080/api/pollution/forecast?lat="+lat+"&lng="+lon, {
            method: 'GET',
            mode: 'cors',
        })
    }
    currentData(lat, lon) {
        return fetch("http://localhost:8080/api/pollution/current?lat="+lat+"&lng="+lon, {
            method: 'GET',
            mode: 'cors',
        })
    }
}

export default new Service();