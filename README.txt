Dinuta Eduard-Stefan 321CA
Tema 2 POO - Etapa 2

   Tema continua implementarea backend-ului unei platforme specifice
vizualizarii de filme si seriale folosind conceptele OOP. Fata de prima etapa
am adaugat in plus patternul observer si am implementat urmatoarele functionalitati
prin adaugarea de noi clase de actiuni:
-subscribe: aboneaza utilizatorul curent la unul dintre genuri, urmand sa
primeasca notificare (in noul camp din clasa user) in cazul in care este
adaugat/sters un film din acel gen
-adaugarea/stergerea de filme: se adauga/sterge un nou film din array-ul global
de filme din clasa PlatformGenerator. La aceasta actiune sunt notificati
utilizatorii abonati la unul dintre genurile de filmului folosing patternul
observer. Clasa User implementeaza interfata ObserverObject, mai exact functia
update ce va adauga in array-ul de notificari al userului o noua intrare cu un
mesaj corespunzator. Astfel, la fiecare adaugare sau stergere se apeleaza
functia de update pentru fiecare observer, adica fiecare user care are acces
la filmul respectiv.
-actiunea de back: pentru actiunea de back am adaugat un array in care se
adauga la fiecare actiune de change page pagina de pe care am plecat. La
fiecare actiune de back se va alege ultima pagina adaugata in acest array
si se vor folosi functiile deja implementate de schimbare a paginii, cu
diferenta ca am adauga un parametru de back pentru a nu mai face verificarea
de schimbare pe o pagina valida, pentru ca la actiunea de back aceasta va fii
mereu corecta
-recomandari: la finalul actiunilor, daca userul logat are cont premium se
incearca sa i se dea o recomandare de film. Clasa RecommendAction
implementeaza algoritmul descris de generare a recomandarii, folosind un
TreeMap pentru a pastra genurile in ordine lexicografica. Si aceasta clasa
se foloseste de patternul observer deoarece recomandarea este facuta sub forma
unei notificari. Astfel la finalul algoritmului va fii apelata functia de
update a userului conectat pentru a primi o notificare cu filmul recomandat sau
cu mesajul "No recommendation" daca nu a fost gasit niciun film
    In plus, au fost modificate unele functionalitati deja implementate
pentru a trata cazuri care nu au aparut in testele de la prima etapa. Acum
un user poate sa acorde un nou rating, unui film caruia i-a dat deja rate.
Fiecare user are un HashMap in care este retinut ratingul acordat fiecarui
film. La schimbarea ratingului, cel vechi este sters din array-ul de ratinguri
al filmului si este adaugat cel nou, fiind schimbata si valoarea din HashMap.
De asemenea, la actiunea de watch nu se mai afiseaza output daca filmul a fost
deja vizionat de utilizator.

   