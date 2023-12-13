const apiKey = "AIzaSyATbuo_zzaHENrFJKKMLzwP6Hu_nzaFUqc";
const isMobile = navigator.userAgent === 'Iphone' || navigator.userAgent === 'Android';
let cep;
let bairro;

async function obterCepPorGeoLocalizacao() {
        const watcher = navigator.geolocation.watchPosition(async function (position) {
            const latitude = position.coords.latitude;
            const longitude = position.coords.longitude;
            const geocodingResponse = await fetch(`https://maps.googleapis.com/maps/api/geocode/json?latlng=${latitude},${longitude}&key=${apiKey}`);
            if (geocodingResponse.ok) {
                const geocodingData = await geocodingResponse.json();
                cep = extrairCep(geocodingData);
            } else {
                throw new Error("Erro ao obter o CEP da geolocalização");
            }
        });
}

function extrairCep(geocodingData) {
    if (geocodingData.results.length > 0) {
        for (let i = 0; i < geocodingData.results[0].address_components.length; i++) {
            if (geocodingData.results[0].address_components[i].types[0] === "postal_code") {
                return geocodingData.results[0].address_components[i].long_name;
            }
        }
    }
    return null;
}

async function consultarBairroPorCep(cep) {
    cep.toString().replace("-", "");
    const response = await fetch(`https://viacep.com.br/ws/${cep}/json`);
    if (response.ok) {
        let json = await response.json();
        bairro = json.bairro
    } else {
        console.error("Bairro não encontrado por cep: "+cep);
        return null;
    }
}

const obterBairroAtual = async () => {
    await obterCepPorGeoLocalizacao();
    setTimeout(async function () {
        await consultarBairroPorCep(cep);
        console.log(bairro);
    }, 3000);
}

if ('geolocation' in navigator && isMobile) {
    obterBairroAtual().then(setarBairro());
} else {
    console.error("Geolocalização não suportado ou não é precisa no dispositivo usado!")
}

function setarBairro() {
    setTimeout(async function () {
        console.log(bairro);
        let response = await fetch(`/geolocalizacao/${bairro}`)
        let recarregarTela = await response.json();
        console.log(recarregarTela);
        if (recarregarTela === true) {
            location.reload();
        }
    }, 5000);
}
