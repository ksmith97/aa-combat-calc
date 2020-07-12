(ns com.deadron.aacombatcalc.core)

(def all-units {
                :infantry        [1/6 1/3]
                :infantry+       [1/3 1/3]                  ; When infantry
                :tank            [1/2 1/3]
                :artillery       [1/3 1/3]
                :fighter         [1/2 2/3]
                :bomber          [2/3 1/6]
                :submarine       [1/3 1/6]
                :destroyer       [1/3 1/3]
                :cruiser         [1/2 1/2]
                :battleship      [2/3 2/3]
                :aircraftcarrier [1/6 1/3]
                :transport       [0 0]
                })
; example result
;{
; :avg       5
; :prestrike [2/3 1/3]
; :battle
;}

(defn unit-to-attack-stat [unit count] (->> all-units
                                            (unit)
                                            (first)
                                            (repeat count)))

(defn unit-to-defend-stat [unit count] (->> all-units
                                            (unit)
                                            (last)
                                            (repeat count)))

; Example units value
;{
; :infantry 10
; :tank     5
;}

(defn promote-units "Promotes infantry to infantry+ based on matching number of artillery"
  [units]
  (let [infantry (:infantry units 0)
        artillery (:artillery units 0)
        promotions (min artillery infantry)]
    (-> units
        (assoc-in [:infantry] (- infantry promotions))
        (assoc-in [:infantry+] promotions))))

(defn expand-unit-count-to-odds [stat-fn [unit-keyword quantity]]
  (stat-fn unit-keyword quantity))

(defn hit-stats [units expand-fn]
  (let [promoted-units (promote-units units)
        unit-hit-chance (mapcat #(expand-unit-count-to-odds expand-fn %) promoted-units)]
    {:avg (apply + unit-hit-chance)}))

(defn attacking-hit-stats [units]
  (hit-stats units unit-to-attack-stat))

(defn defending-hit-stats [units]
  (hit-stats units unit-to-defend-stat))

; Example stat
;{
;   :avg 2N
;}