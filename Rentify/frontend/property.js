document.addEventListener('DOMContentLoaded', async () => {
    const response = await fetch('/api/properties');
    const properties = await response.json();
    const propertiesList = document.getElementById('propertiesList');
    propertiesList.innerHTML = properties.map(property => `
        <div>
            <h3>${property.place}</h3>
            <p>Area: ${property.area}</p>
            <p>Bedrooms: ${property.numberOfBedrooms}</p>
            <p>Bathrooms: ${property.numberOfBathrooms}</p>
            <p>Nearby Hospitals: ${property.nearbyHospitals}</p>
            <p>Nearby Colleges: ${property.nearbyColleges}</p>
            <button onclick="expressInterest(${property.id})">I'm Interested</button>
        </div>
    `).join('');
});

async function expressInterest(propertyId) {
    const response = await fetch(`/api/properties/${propertyId}`);
    const property = await response.json();
    alert(`Contact the seller at: ${property.user.email}`);
}
