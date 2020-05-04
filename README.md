# Johannes Zottele - 11911133

## Aufgabenblatt 5
### Zusatzfragen:

1. Durch das entfernen der überschriebenen equals-Methode, wird bei einem neuen Eintrag gecheckt ob die Objekt Referenz die selbe ist wie die einer anderen. Daher werden Bodies mit dem selben Namen aber unterschiedlicher Referenz in die Map aufgenommen. Daher kann es sein dass Bodies doppelt vorkommen, obwohl sie den selben Namen haben.
2. Die Indexstellen für die Einträge verändern sich, da sich der HashCode nicht mehr auf den Namen des Bodies bezieht.
3. Übergebene Parametern `x.equals(...)`:
	4. null: false
	5. x: true
	6. y: liefert selbes Ergebnis wie `y.equals(x)`
	7. Wenn `x.equals(y)` und `y.equals(z)` dann auch `x.equals(z)`
	8. Liefert (ohne Veränderung) immer selbe Resultate.
9. Nein, da bei der equals-Methode nur der Name der Objekte verglichen wird aber nicht der gesamte Inhalt. Dadurch kann es sein dass ein Objekt nicht aufgenommen wird, weil es den gleichen Namen hat, obwohl der Inhalt unterschiedlich ist. 
10. Man müsste das Interface nur implementieren, die methoden contains und equals zu `CelestialSystemIndexTree` hinzufügen und die add Method leicht modifizieren, bzw eine Overload-Method hinzufügen.