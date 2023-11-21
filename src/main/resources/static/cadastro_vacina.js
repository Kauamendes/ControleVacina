function obterLocalizacao() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            function (position) {
                let latitude = position.coords.latitude;
                let longitude = position.coords.longitude;

                // Use a API de Geocodificação do Google Maps para obter o bairro
                let geocodingApiUrl = `https://maps.googleapis.com/maps/api/geocode/json?latlng=${latitude},${longitude}&key=AIzaSyATbuo_zzaHENrFJKKMLzwP6Hu_nzaFUqc`;

                fetch(geocodingApiUrl)
                    .then(response => response.json())
                    .then(data => {
                        let bairro = extrairBairro(data);
                        console.log("Bairro: " + bairro);
                        return bairro;
                    })
                    .catch(error => {
                        console.error("Erro ao obter informações de geocodificação: " + error);
                    });
            },
            function (error) {
                console.error("Erro ao obter a localização: " + error.message);
            }
        );
    } else {
        console.error("Geolocalização não suportada pelo navegador.");
    }
}

function extrairBairro(geocodingData) {
    // Implemente a lógica para extrair o nome do bairro a partir dos resultados
    // Aqui, estamos assumindo que o nome do bairro está na primeira parte do endereço
    if (geocodingData.results.length > 0) {
        return geocodingData.results[0].address_components[0].long_name;
    }
    return "Bairro não encontrado";
}

document.getElementById("bairro_id").innerHTML(obterLocalizacao());