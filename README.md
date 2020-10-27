# Trabajo Práctico - Introducción a la Orientación a Objetos
*UADE* - _2° Cuatrimestre 2020_

![alt-text][logo]

[logo]: https://www.uade.edu.ar/media/kh1hx5wh/logo_mesa-de-trabajo-1-copia.png "UADE Logo"
### Grupo 5

### Autores
* Barreto Molas Reinaldo
* Calciano Federico
* Castilla Gonzalo Martín
* Gelsi Federico
* Lafranconi Juan Cruz
* Laporte Gabriel
* Toreta Franco

***



## Índice

- [El proyecto](#el-proyecto)
  * [Sociedades de Garantías Recíprocas (SGR)](#sociedades-de-garant-as-rec-procas--sgr-)
  * [Socios Partícipes](#socios-part-cipes)
  * [Socios Protectores](#socios-protectores)
  * [De socios postulantes a socios plenos](#de-socios-postulantes-a-socios-plenos)
  * [Líneas de crédito](#l-neas-de-cr-dito)
  * [Solicitud de Garantías](#solicitud-de-garant-as)
  * [Operatoria diaria](#operatoria-diaria)
  * [Riesgo vivo de un socio vs utilizado de línea](#riesgo-vivo-de-un-socio-vs-utilizado-de-l-nea)
  * [Desembolsos y recuperos](#desembolsos-y-recuperos)
- [Reglas de negocio](#reglas-de-negocio)
- [Consultas generales](#consultas-generales)
- [Módulos a entregas](#m-dulos-a-entregas)
- [Módulos por cantidad de integrantes](#m-dulos-por-cantidad-de-integrantes)
- [Documentación y fases de entrega](#documentaci-n-y-fases-de-entrega)
- [Pautas para la aprobación de las entregas](#pautas-para-la-aprobaci-n-de-las-entregas)
- [Pautas para la Aprobación del Trabajo Práctico Cuatrimestral](#pautas-para-la-aprobaci-n-del-trabajo-pr-ctico-cuatrimestral)
- [Cronograma de entregas y entregables](#cronograma-de-entregas-y-entregables)






***

## El proyecto

### Sociedades de Garantías Recíprocas (SGR)

Las Sociedades de Garantía Recíproca (SGR) son entidades financieras cuyo objeto principal consiste en facilitar el acceso al crédito de las pequeñas y medianas empresas (pymes) y mejorar, en términos generales, sus condiciones de financiación, a través de la prestación de avales ante bancos, cajas de ahorros y cooperativas de crédito, administraciones públicas y clientes y proveedores. 

Usted es contratado para realizar un sistema de gestión para una SGR.

Las SGRs se componen por socios. Los socios pueden ser de dos tipos: Protectores y Partícipes. 

### Socios Partícipes

Cuando una empresa solicita ser socia de una SGR se la ingresa al sistema como “postulante a socio partícipe” y se guardan datos básicos de la misma (cuit, razón social, fecha de inicio de actividades, tipo -pequeña, mediana-, actividad principal, dirección, teléfono y correo electrónico. También se le solicita información de sus accionistas (cuit, razón social y porcentaje de participación). 

La empresa debe presentar documentación que es controlada por la SGR y tiene que ser registrada en el sistema. La registración consta de un tipo de documento (Estatuto/Contrato Social, Copia de últimos 3 balances certificados, Manifestación de bienes de los socios, etc.), fecha de recepción, estado (ingresado, controlado, rechazado) y usuario que lo cargó. Hay documentación que es de presentación obligatoria y otra que es deseable que la empresa presente.

### Socios Protectores

Cuando una empresa solicita ser socia protectora de una SGR se la ingresa al sistema como “postulante a socio protector” y se guardan datos básicos de la misma (cuit, razón social, fecha de inicio de actividades, tipo -pequeña, mediana-, actividad principal, dirección, teléfono y correo electrónico. También se le solicita información de sus accionistas (cuit, razón social y porcentaje de participación). 

La empresa debe presentar documentación que es controlada por la SGR y tiene que ser registrada en el sistema. La registración consta de un tipo de documento (Estatuto/Contrato Social, Copia de últimos 3 balances certificados, Manifestación de bienes de los socios, etc.), fecha de recepción, estado (ingresado, controlado, rechazado) y usuario que lo cargó. Hay documentación que es de presentación obligatoria y otra que es deseable que la empresa presente.

Los socios protectores hacen aportes (dinero) al fondo de riesgo de la SGR. Los aportes hechos a la SGR tienen una vigencia de 2 años luego de los cuales la empresa protectora puede retirar su aporte.

### De socios postulantes a socios plenos

Una vez que la documentación está completa y el postulante es aceptado, la empresa puede suscribir acciones de la SGR y así ser socio de esta. En esta SGR se realizan transferencias por lo que un socio existente con saldo de acciones debe venderle a la nueva empresa acciones para finalizar el trámite y convertirse de “postulante a socio” a “Socio pleno”. Los socios partícipes suscriben acciones tipo A y los socios protectores acciones de tipo B. Una vez suscriptas las acciones el socio puede operar con la SGR.

### Líneas de crédito

Cuando una empresa se convierte en socio partícipe se le asigna una línea de crédito por un monto a determinar. Las líneas de crédito tienen fecha de vigencia por lo que un socio con una línea vencida no puede operar.
Cuando una línea es aprobada se la vincula con qué tipo de operaciones puede operar la empresa. Los tipos de operaciones son:
**Tipo 1:**
* Cheques propios
* Cheques de terceros
* Pagaré Bursatil
**Tipo 2:**
* Cuentas corrientes Comerciales
* Tarjetas de crédito
**Tipo 3:**
* Préstamos

Para operar por el total de la línea asignada la empresa debe presentar contragarantías para poder operar.
Los tipos de contragarantías son:
* Fianza Personal
* Hipotecas
* Pagaré

Una empresa que quiera realizar una operación sólo podrá hacerlo por el total de contragarantías presentadas. En caso en que las contragarantías presentadas superen el total de la línea asignada, el total de la línea operará como tope de operación.

### Solicitud de Garantías

Cuando un socio solicita realizar una operación debe presentar cierta información según el tipo: 
* **Cheques:**
  - Banco del cheque 
  - Número de cheque 
  - Fecha de vencimiento
  - CUIT del Firmante 
* **Cuentas corrientes Comerciales**
  - Empresa con la que tendrá cuenta corriente
  - Importe total
  - Fecha de vencimiento 
* **Préstamos**
  - Banco donde se solicita el préstamo
  - Importe Total
  - Tasa
  - Fecha de acreditación
  - Cantidad de cuotas
  - Sistema (Francés/Alemán/Americano)

### Operatoria diaria

Todas las operaciones se cargan en un estado inicial que es “ingresado”. Si la operación puede cursarse por la línea asignada, se le emite un certificado de garantía a nombre del socio y la operación cambia de estado a “Con certificado emitido”. El número de certificado de garantía es un dato fundamental para identificar la operación. 
 
A diferencia de los préstamos y las cuentas corrientes comerciales, las operaciones de cheques son cheques cuyo beneficiario es el propio socio que decide negociarlos antes de la fecha de vencimiento. Los cheques se presentan en el mercado Argentino de Valores y se venden con una “tasa de descuento” que es el % que le quitan al importe del cheque por cobrarlo con anterioridad al vencimiento. 
 
Si la operación presentada prospera en el mercado o banco y el socio recibe el dinero la operación pasa a estado “Monetizado” y se le calcula una comisión al socio. 

Las comisiones se calculan según la siguiente tabla:

| Tipo de operación | Comisión |
| :---------------: | :------: |
| **1** | 3% |
| **2** | 3% |
| **3** | 4% |

Cada comisión se crea con un estado inicial de “calculada” que sólo cambia a “facturada” al momento de emitir una factura a nombre del socio lo cual suele ocurrir el primer día hábil de la semana. 
 
Cada cambio de estado en socios, documentación, operaciones, etc debe ser identificada con la fecha en que se realiza el cambio, estado anterior, estado nuevo, una referencia (con tipo) y quien realizó el cambio. 

### Riesgo vivo de un socio vs utilizado de línea

El riesgo vivo de un socio son todas las operaciones monetizadas aún no vencidas. El riesgo vivo se compone por: 
* **Operaciones tipo 1:** Importe total de la operación.
* **Operaciones tipo 2:** Importe utilizado.
* **Operaciones tipo 3:** Cuotas impagas

El utilizado de la línea se compone por todas las operaciones con certificado emitido más el riesgo vivo del socio.

### Desembolsos y recuperos

Cuando un socio no cubre alguna de las operaciones que presentó, la que paga la deuda es la SGR. Luego, el socio adquiere una deuda con la SGR que debe cancelar. La cancelación de los desembolsos hechos por la SGR puede se realizan mediante lo que se denominan recuperos y pueden ser parciales o totales. La SGR puede optar por cobrar un monto por mora o no.

## Reglas de negocio

* Ningún socio puede operar por más del 5% del FDR
* La SGR no puede recibir más del 5% del FDR en cheques de un mismo firmante
* Ningún socio puede operar si debe facturas por más del 10% del total de la línea asignada
* Ningún socio puede operar con desembolsos no cubiertos
* Un socio no puede ser aprobado como protector si es accionista de una empresa socia partícipe de la SGR
* Su una empresa comparte accionistas con otra empresa el total computado para el 5% del FDR es la suma de los riesgos vivos de ambas empresas

## Consultas generales

* Total de comisiones calculadas en un día por operaciones de cheques presentadas en el Mercado Argentino de Valores 
* Las operaciones avaladas a nombre de un socio, en estado monetizadas en un período de tiempo 
* Valor promedio de la tasa de descuento y total operado de cheques y pagarés para un tipo de empresa (pequeña, mediana, grande), en un período de tiempo 
* Consulta porcentaje de comisión a calcularle a un socio por un tipo de operación pasada por parámetros 
* Consulta de saldo mora. Mora de un socio por día.
* Consulta consolidada de un socio. Consultas riesgo vivo y total de utilizado. Total y detalle. Contragarantias.

## Módulos a entregas

* Socios
  - Documentación
  - Accionistas
* Líneas y tipos de operaciones asociadas
  - Contragarantías
* Operaciones
  - Cheques y pagarés
  - Cuentas corrientes comerciales y tarjetas de crédito
  - Préstamos
* Desembolsos y recuperos
  - Administración y consultas
* Consultas generales

## Módulos por cantidad de integrantes

Los grupos con 4 integrantes están eximidos de entregar:
* Desembolsos y recuperos
* Contragarantías
* Consultas de saldo Mora

Los grupos con 5 integrantes están eximidos de entregar:
* Recuperos
* Contragarantías

Los grupos con 6 integrantes están eximidos de entregar:
* Recuperos

La no entrega de un módulo exime, también, la entrega la parte del modelo y diagrama de secuencia asociado.

## Documentación y fases de entrega

Se pide documentar el diseño del sistema e implementar en base a las siguientes fases:
* **Primera Fase:** Diagrama de clases.
* **Segunda Fase:** Diagrama de secuencias de todas las reglas de negocios y consultas generales
* **Tercera Fase:** Sistema funcionando

## Pautas para la aprobación de las entregas

1.	Todas las entregas deberán tener una carátula indicando nombre de integrantes, fecha y número de entrega.
2.	Las entregas deben subirse al grupo creado en la plataforma webcampus.
3.	Incorporar entregas anteriores con correcciones si corresponde
4.	Respetar la fecha de entrega indicada por la cátedra.
5.	**Se considera desaprobada la entrega que no cumple alguna de las consignas anteriores**

## Pautas para la Aprobación del Trabajo Práctico Cuatrimestral

1.	Cumplir con todas las entregas definidas por la cátedra en la fecha establecida.
2.	Aprobar todas las entregas definidas por la cátedra.
3.	Respetar las consignas solicitadas y el orden definido.

## Cronograma de entregas y entregables

* Primera entrega: **15-16/SEP/2020**
  - Primera fase completa
* Segunda entrega: **27-28/OCT/2020**
  - Segunda fase completa
* Entrega Final: **17-18/NOV/2020**

