(ns com.deadron.aacombatcalc.core_test
  (:use clojure.test com.deadron.aacombatcalc.core))

(testing "Infantry promotions"

  (testing "Zero infantry and atrillery"
    (is (= {:infantry 0 :infantry+ 0 :artillery 0} (promote-units {:infantry 0 :artillery 0}))))

  (testing "Infantry promotions equal number of infantry and artillery"
    (is (= {:infantry 0 :infantry+ 1 :artillery 1} (promote-units {:infantry 1 :artillery 1}))))

  (testing "Infantry promotions more artillery than infantry"
    (is (= {:infantry 0 :infantry+ 1 :artillery 2} (promote-units {:infantry 1 :artillery 2}))))

  (testing "Infantry promotions more infantry than artillery"
    (is (= {:infantry 1 :infantry+ 2 :artillery 2} (promote-units {:infantry 3 :artillery 2}))))

  (testing "Infantry promotions no units"
    (is (= {:infantry 0 :infantry+ 0} (promote-units {})))))

(testing "Average attacking combat hits"
  (testing "Basic Infantry"
    (is (= {:avg 1N} (attacking-hit-stats {:infantry 6})))))

(testing "Average defending combat hits"
  (testing "Basic Infantry"
    (is (= {:avg 2N} (defending-hit-stats {:infantry 6})))))
