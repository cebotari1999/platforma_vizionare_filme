# Platforma vizionare filme
Cebotari Zinaida, 2020

Implementarea este realizata intr-un pachet separat, Solutions. Acesta contine o clasa principala Actions, care are trei subclase, Command, Query, Recommendation. Aceste clase contin metodele care realizeaza comenzile, queries si recomandarile. Pachetul mai contine doua clase Ratings si Sort.

Ratings cotine doi membri, de tip Map. In ratings, se salveaza denumirea filmului/serialului, iar in normberOfRatings se salveaza denumirea filmului/serialului si numarul de ratinguri oferite. Contine un constructor pentru initializare, o metoda prin care se poate verifica daca un film/serial a primit rating, metode pentru a adauga rainguri si un getter care returneaza ratingul final al unui film/serial. Clasa Actions contine un membru de tip Ratings pentru a avea acces direct la metodele ei.

Sort este o clasa care are cativa membri de tip ArrayList, utilizati pentru a sorta un obiect de tip Map. Cand se apeleaza o metoda a clasei,  in primul ArrayList se salveaza string-urile in oridine alfabetica, in al doilea valorile in oridine crescatoare. Pentru sortare se parcurge vectorul de stringuri si cel de valori, se verifica daca valoarea din obiectul Map pentru stringul respectiv sa fie egala cu valoarea din vectorul de valori. Astfel se sorteaza un Obiect de tip Map ascendent. Pentru a sorta descendent, vectorii se parcurg de la coada la cap. Rezultatul sortarii este scris intr-un alt membru de tip ArrayList al clasei, care este returnat de catre metoda.

Sort mai contine o alta metoda editArray, care reduce un ArrayList la o lungime N, prin eliminarea elementelor pe poziitile cu valoarea mai mare de cat N. Clasa Actions contine un membru de tip Sort pentru a avea acces direct la metodele ei.

Clasa Actions are un membru de tip Input, in care se stocheaza datele de intrare, un membru de tip Write, in care se scrie, un array de Tip JSON care este populat cu stringuri ce urmeaza a fi scrise in fisierul de output , un membru de tip Ratings si Sort.Acesta contine un constructor pentru a initializa toti membrii si getters prin care se face acces la ele. Clasele copii se folosesc de ele pentru a accesa membrii clasei. Astfel orice clasa copil are acces la toate datele de intrare, specificatiile legate de actiuni, la toate datele legate de seriale, filme, utilizatori, actori, poate adauga rating-uri si are acces la ele, poate realiza sortari.

In main se parcurg toate actiunile din fisierul de input si de aici este punctul de plecare pentru executarea lor(comenzi, queries, recomandari). Se creeaza cate o instanta pentru fiecare clasa(Command, Recommandation, Query) si se initializeaza clasa Actions. Intr-un for se preia fiecare actiune si in functie de tipul acesteia se apeleaza metoda necesara prin intermediul instantei, ca parametru se ofera un numar point, care reprezinta numarul Id al actiunii ce trebuie realizata.

Clasa Command contine cateva metode de sciere prin care se populeaza Array-ul de tip JSON, cu rezultatul comenzilori. In metodele addFavorite/addView/addRating se adauga un video in membrul favoriteMovies/history/rating cu numarul total de viziualizari/ratingul oferit. In metoda addRating se adauga si in membrul ratings al clasei Actions, filmul si ratingul oferit.

Clasa Query contine o metoda writeQuery de scriere, care adauga in Array-ul de tip JSON rezultatul la query. O metoda sortQuery, care sorteaza rezultatul la query, stocat inr-un obiect de tip Map, ascendent/descent, in functie de tipul sortarii si reduce dupa caz rezultatul la un numar de elemente egal cu N. La final apeleaza metoda writeQuery. 

Metodele sortFiltersMovies/sortFiltersShows se slaveaza intr-un obiect de tip Map, toate filmele/serialele care au fost lansate intr-un an si apratin unui gen, date ca filtru. Aceste metode sunt apelate in cadrul altor metode din clasa Query.

Metoda queryAverage. Se parcurg toti actorii, se verifica daca filmele/serialele in care s-au filmat au primit raiting. Se aduna rating-urile finale oferite pentru aceste filme si se face media lor aritmetica. Toti actorii si media rating-urilor pentru filmele in care au jucat se slaveaza intr-un obiect de tip Map, transmis la metoda sortQuery.

Metoda queryAwards. Se parcurg toti actorii din baza de date. Se verifica ca acestia sa contina toate premiile primite ca filtru, daca le contin, se stocheaza numele actorului si numarul total de premii oferite. La final se apeleaza metoda sortQuery.

Metoda queryFilterDescrition. Se verifica ca descrierea actorilor s-a contina toate cuvintele cheie primite ca parametru. Daca le contin, acestia se stocheaza, intr-un obiect de tip ArrayList. La final se sorteaza dupa parametrii primiti si se adauga rezultatul la query in arrayResult.

Metodele queryRaitingMovies/queryRaitingShows se folosesc de metodele sortFiltersMovies/sortFiltersShows. Pentru filmele/serialele obtinute in urma la sortFiltersMovies/sortFiltersShows se verifica daca au rating, daca da se stocheaza mapMovies/mapShows, apoi se apeleaza metoda sortQuery pentru mapMovies/mapShows.

Pentru metoda queryFavoriteMovies/queryFavoriteShow se apeleaza sortFiltersMovies/sortFiltersShows. Se verifica care din ale se gasesc in lista de filme favorite a utilizatorilor. Daca un film este in lista de filme favorite a mai multor utilizatori, se stocheaza numarul de aparitii in lista de favorite a tuturor utilizatorilor. La final se aplica sortQuery.

In metodele queryLongestMovies/queryLongestShows, se parcurg toate filmele/serialele, se verifica daca acestea corespund parametrilor ceruti, daca da se stocheaza filmele/serialele si durata lor in mapLongestMovies/mapLongestShows.Se apeleaza metoda sortQuery pentru mapLongestMovies/mapLongestShows.


In metodele queryMostViewedMovies/queryMostViewedShows, se parcurg toate filmele/serialele, se verifica daca acestea corespund parametrilor ceruti, daca da se stocheaza filmele/serialele in mapMostViewed.Se parcurg apoi toti utilizatorii si se verifica daca filmele stocate au fost vizionete si se stocheaza cu numarul total de viziualizari primit de la toti utilizatorii. Se apeleaza metoda sortQuery pentru mapMostViewed.

Metoda queryUsers. Se stocheaza toti utilizatorii si numarul total de ratinguri pe care l-au oferit, apoi se apeleaza sortQuery.

Clasa Recommendation, cotine o metoda pentru a adauga in arrayResult rezultatul recomandarii cerute. 

Metoda standart parcurge toate filmele si serialele din baza de date.Se returneaza primul film sau serial nevizionat de utilizator. 

Metoda bestUnseen, se sorteaza filmele si serialele dupa rating-ul lor si se returneaza primul film nevizionat de utilizator. Daca toate filmele, serialelecare au primit rating au fost vizionate, se returneaza primul film nevizualizat din baza de date.

Metoda popular. Se parcurge istoricul la toti utilizatorii si se stocheaza toate filmele si serialele vizionate cu numarul total de vizualizari. Apoi se parcurg toate filmele si serialele si se stocheaza toate genurile. Se verifica apoi fiecare film, serial carui gen apartine si se aduna pentru fiecare gen numarul de vizualizari al filmelor respective. Se sorteaza genurile descrescator. Se parcurg toate filmele si se verifica ca sa apartina primului gen din lista, procedura se repeta pana la ultimul gen, pentru a returna primul film nevizualizat.

Metoda favourite. Se parcurg toti utilizatorii si se insumeaza numarul de aparitii a filmelor in campul filmelor favorite. Apoi se sorteaza in functie de numarul de aparitie in lista filmelor favorite si se recomanda primul film pe care nu la vazut utilizatorul.

Metoda search. Se parcurg toate filmele, pentru a verifica in ce genuri se incadreaza. Daca contin genul dat ca parametru se stocheaza. La final se sorteaza si se recomanda primul film neviziualizat din acest gen.
