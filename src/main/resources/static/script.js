const API_URL = 'https://proyectofinal-ynd2.onrender.com/api/productos';


// Forzar que la página cargue en el tope superior
document.addEventListener('DOMContentLoaded', () => {
    window.scrollTo(0, 0);
    cargarProductos();
});


// 1. Función para Listar (GET)
async function cargarProductos() {
    try {
        const respuesta = await fetch(API_URL); // Petición al backend
        const productos = await respuesta.json(); // Convertimos a JSON

        const contenedor = document.getElementById('contenedor-productos');
        contenedor.innerHTML = ''; // Limpiamos antes de cargar

        productos.forEach(p => {
            contenedor.innerHTML += `
                <div class="col-md-6 col-lg-4 d-flex align-items-stretch"> <div class="card shadow-sm w-100">
                        <img src="${p.imagenUrl || 'https://via.placeholder.com/150'}" class="card-img-top product-img" alt="${p.nombre}">
                        <div class="card-body">
                            <div>
                                <span class="badge badge-anime mb-2">${p.anime}</span>
                                <h5 class="card-title" style="color: white !important;">${p.nombre}</h5>
                                <p class="price-text">$${p.precio.toFixed(2)}</p>
                                <p class="text-muted small">Stock: <strong>${p.stock}</strong> unidades</p>
                            </div>

                            <div class="mt-auto-custom d-flex gap-2">
                                <button onclick="prepararEdicion(${p.id}, '${p.nombre}', '${p.anime}', ${p.precio}, ${p.stock}, '${p.imagenUrl}')"
                                        class="btn btn-outline-primary btn-sm flex-grow-1">Editar</button>
                                <button onclick="eliminarProducto(${p.id})"
                                        class="btn btn-outline-danger btn-sm">Borrar</button>
                            </div>
                        </div>
                    </div>
                </div>
            `;
        });
    } catch (error) {
        console.error("Error al cargar:", error);
        alert("No se pudo conectar con el servidor. ¿Está encendido el Backend?");
    }
}

// 2. Función para Guardar (POST)
document.getElementById('formProducto').addEventListener('submit', async (e) => {
    e.preventDefault();

    const producto = {
        nombre: document.getElementById('nombre').value.trim(),
        anime: document.getElementById('anime').value.trim(),
        precio: parseFloat(document.getElementById('precio').value),
        stock: parseInt(document.getElementById('stock').value),
        imagenUrl: document.getElementById('imagenUrl').value.trim()
    };

    try {
        const respuesta = await fetch(API_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(producto)
        });

        const data = await respuesta.json(); // Intentamos leer la respuesta del GlobalExceptionHandler

        if (respuesta.ok) {
            alert("¡Producto registrado con éxito!");
            document.getElementById('formProducto').reset();
            cargarProductos();
        } else {
            // Si el backend mandó errores de validación, los mostramos bonitos
            const mensajeError = data.mensaje || JSON.stringify(data);
            alert("Error del servidor: " + mensajeError);
        }
    } catch (error) {
        alert("Error de conexión con la MultiVerse GeeK API");
    }
});

// 3. Función para Eliminar (DELETE)
async function eliminarProducto(id) {
    if (confirm('¿Estás seguro de que deseas eliminar esta figura de colección?')) {
        try {
            const respuesta = await fetch(`${API_URL}/${id}`, {
                method: 'DELETE'
            });

            if (respuesta.ok) {
                cargarProductos(); // Refrescamos la lista
            } else {
                alert("No se pudo eliminar el producto.");
            }
        } catch (error) {
            console.error("Error:", error);
        }
    }
}

// 4. Función para Editar (PUT/PATCH flexible)
async function prepararEdicion(id, nombre, anime, precio, stock, imagenUrl) {
    // Pedimos los nuevos datos (si se cancela o deja vacío, el backend mantendrá el anterior)
    const nuevoNombre = prompt("Nuevo nombre (deja vacío para no cambiar):", nombre);
    const nuevoAnime = prompt("Nuevo anime:", anime);
    const nuevoPrecio = prompt("Nuevo precio:", precio);
    const nuevoStock = prompt("Nuevo stock:", stock);
    const nuevaImagen = prompt("Nueva URL de imagen:", imagenUrl);

    // Creamos el objeto. Si el usuario no escribió nada, enviamos null para que el backend lo ignore
    const productoEditado = {
        nombre: nuevoNombre || null,
        anime: nuevoAnime || null,
        precio: nuevoPrecio ? parseFloat(nuevoPrecio) : null,
        stock: nuevoStock ? parseInt(nuevoStock) : null,
        imagenUrl: nuevaImagen || null
    };

    try {
        const respuesta = await fetch(`${API_URL}/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(productoEditado)
        });

        if (respuesta.ok) {
            alert("¡Producto actualizado!");
            cargarProductos();
        } else {
            const errorData = await respuesta.json();
            alert("Error al actualizar: " + JSON.stringify(errorData));
        }
    } catch (error) {
        alert("Error de conexión al intentar editar.");
    }
}