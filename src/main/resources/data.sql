INSERT INTO servicios_bancarios (codigo, nombre, sector, soporte_tecnico, cantidad_personas_atiende, atencion_exclusiva) VALUES
    ('PZOF', 'Plazos Fijos', 'S1', false, 0, false),
    ('PRES', 'Prestamos', 'S1', true, 1000, false),
    ('CHEQ', 'Cheques', 'S2', true, 1000, true),
    ('TECH', 'Tecnologia', 'S3', true, 10000, true);

INSERT INTO clientes (dni, nombre, apellido, calle, numero, codigo_postal, telefono, celular, tipo_cliente) VALUES
    (30123123, 'Juan', 'Garcia', '9 de Julio', 123, 1100, '11 12312312', null, 'PARTICULAR'),
    (27894567, 'María', 'Pérez', 'Mitre', 456, 1234, null, '11 45645645', 'EMPRESA'),
    (33456789, 'Pedro', 'López', 'Corrientes', 789, 1414, '11 78978978', null, 'PARTICULAR'),
    (20345678, 'Ana', 'Fernández', 'Sarmiento', 345, 1642, '11 34534534', null, 'PARTICULAR'),
    (31234567, 'Diego', 'Martínez', 'Belgrano', 234, 1870, null, '11 23423423', 'EMPRESA'),
    (29876543, 'Laura', 'Gómez', 'Independencia', 567, 2000, '11 56756756', null, 'PARTICULAR'),
    (32145678, 'Eduardo', 'Rodríguez', 'Rivadavia', 890, 2121, null, '11 89089089', 'EMPRESA'),
    (28765432, 'Sofía', 'Díaz', 'San Martín', 123, 2232, '11 12312312', null, 'PARTICULAR');

INSERT INTO clientes_servicios_bancarios (servicio_bancario_id, cliente_id) VALUES
    (SELECT id FROM servicios_bancarios WHERE codigo = 'PZOF', SELECT id FROM clientes WHERE dni = 30123123),
    (SELECT id FROM servicios_bancarios WHERE codigo = 'PZOF', SELECT id FROM clientes WHERE dni = 33456789),
    (SELECT id FROM servicios_bancarios WHERE codigo = 'PZOF', SELECT id FROM clientes WHERE dni = 29876543),
    (SELECT id FROM servicios_bancarios WHERE codigo = 'PZOF', SELECT id FROM clientes WHERE dni = 32145678),
    (SELECT id FROM servicios_bancarios WHERE codigo = 'PZOF', SELECT id FROM clientes WHERE dni = 28765432),
    (SELECT id FROM servicios_bancarios WHERE codigo = 'PRES', SELECT id FROM clientes WHERE dni = 29876543),
    (SELECT id FROM servicios_bancarios WHERE codigo = 'PRES', SELECT id FROM clientes WHERE dni = 33456789),
    (SELECT id FROM servicios_bancarios WHERE codigo = 'CHEQ', SELECT id FROM clientes WHERE dni = 30123123),
    (SELECT id FROM servicios_bancarios WHERE codigo = 'CHEQ', SELECT id FROM clientes WHERE dni = 29876543),
    (SELECT id FROM servicios_bancarios WHERE codigo = 'CHEQ', SELECT id FROM clientes WHERE dni = 33456789),
    (SELECT id FROM servicios_bancarios WHERE codigo = 'CHEQ', SELECT id FROM clientes WHERE dni = 32145678),
    (SELECT id FROM servicios_bancarios WHERE codigo = 'TECH', SELECT id FROM clientes WHERE dni = 30123123);


