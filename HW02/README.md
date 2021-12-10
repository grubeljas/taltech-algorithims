[comment]: <> (
SEE ON MARKDOWN FAIL, PALUN VAATA SEDA PROGRAMMIS, MIS
VÕIMALDAB MARKDOWNI KUVADA.
)

# HW02 Seletuskiri

## Lahendusmeetodid
#### 1. Dijkstra algoritm
Dijkstra algoritm andis kõige parema tulemuse. Algoritmil on front, mida ta
järjest laiendab. Igal sammul to valib frondist kõige esimese elemendi ning
leiab sellele elemedile vastavad naaber elemendid.  Kui naaber elemendid ei
ole juba külastatud positsioonide hulgas, siis ta lisab selle fronti. Front
on implementeeritud _priority queue'na_. Elemendi prioriteet järjekorras on
tema juba tehtud hüpete arv. Kui hüpete arv on sama, siis suurem prioriteet
on sellel elemendil, millel on väikseim täielik trahv. Igal positsioonil ta
salvestab _hash map'i_ naaber positsiooni võtmena ning praeguse positsiooni
väärtusena. Kui on jõudnud lõpp positsioonile on võimalik panna kokku terve
teekond jälgides _hash map'i_.

#### 2. Modified Dijkstra algoritm
See algoritm töötab täpselt samamoodi,  ainuke erinevus on selles, et front
on seekord hash map, mis hoiab _priority queue_ hüpete arvu järgi. Idee oli
proovida _priority queue'sse_ lisamist kiirendada. Kuna igas järjekorras on
vähem elemente peaks sinna lisamine olema kiirem, kuid testides ei andnud
see meetod paremat tulemust.

#### 3. Mapped Walls algoritm
Töötab Dijkstra algoritmil, erinevus on selles kuidas naabreid valitakse.
Alguses teeb puu igast reast ja tulbast, kuhu salvestab kõik ruudud, millel
on sein. Naabreid otsides ei ole vaja kõik järgmised ruudud üle kontrollida
seina olemasolule, seinad on võimalik leida puust O(logn) ajas.


## Korrektse lahenduse leidmine
Dijkstra algoritm leiab alati korrektse lahenduse kuna ta ei ole ahne. Igal
sammul ta vaatab seda elementi, millele astumine oleks miinimum kaaluga,
seega ta ei astu kunagi sinna, kuhu saaks mingit kiiremat teed pidi minna.
Lõpptulemus on selline, et ta leiab kõige lühema tee algpunktist igasse
teise punkti, mida ta otsides külastas.


## Lahenduse keerukus
Halvim juhtum algoritmi jaoks on kui ei ole ühtegi seina. Sellisel juhul on
algoritmi keerukus O(n^2 logn), kuna ta peab läbi käima kõik batuudid ning
kontrollima naabreid igal batuudil, mis on logn ajas. Parimal juhul on
võimalik saada algpunktist lõpppunkti 2 hüppega (1 hüppega kui kaart on 1
dimensioonile). Seega on keerukus O(n) kuna ta peab ikka kontrollima
hüpates kas ette tuleb sein või mitte. Keskmine keerukus oleks O(n logn logn)
kuna ta peab vähemalt läbima terve külje pikkusega n ning kontrollima igal
sammul seinade olemasolu. Lisaks teine logn kuna ta otsib mööda teisi teid ka.


## Lahendusmeetodite võrdlus
Kõik algoritmid on mõtte poolest samad, kuid erinevad sisemises
implementatsioonis. Kõige kiiremaks osutus Dijkstra, teiseks Modified
Dijkstra, kolmandaks Mapped Walls.

Kõik testid on tehtud = versiooniga.

#### 10x10 map average time over 10000 cycles
| 10x10             | Avg time (ms) | Total time (ms) |
|-------------------|---------------|-----------------|
| Dijkstra          | 0.0272        | 272             |
| Modified Dijkstra | 0.0364        | 364             |
| Mapped Walls      | 0.0316        | 316             |

#### 100x100 map average time over 1000 cycles
| 100x100           | Avg time (ms) | Total time (ms) |
|-------------------|---------------|-----------------|
| Dijkstra          | 4.466         | 4466            |
| Modified Dijkstra | 4.747         | 4747            |
| Mapped Walls      | 5.156         | 5156            |

#### 1000x1000 map average time over 20 cycles
| 1000x1000         | Avg time (ms) | Total time (ms) |
|-------------------|---------------|-----------------|
| Dijkstra          | 1974.35       | 39487           |
| Modified Dijkstra | 2046.6        | 40932           |
| Mapped Walls      | 2367.5        | 47350           |
