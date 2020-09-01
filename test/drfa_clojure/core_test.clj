(ns drfa-clojure.core-test
  (:require [clojure.test :refer :all]
            [drfa-clojure.core :refer :all]
            [drfa-clojure.expression :refer :all]
            [clojure.java.io :as io]
            [clojure.pprint :as pp]))

; For running in intellij
;(def base-file-name "../../resources/base.csv")
;(def target-file-name "../../resources/target.csv")

(def base-file-name "resources/base.csv")
(def target-file-name "resources/target.csv")

(def field-indexes (list 0 1 2 4))

(def base-file (split-line (readFile base-file-name)))
(def target-file (split-line (readFile target-file-name)))

(def base-map (to-map base-file (list 0) field-indexes))
(def target-map (to-map target-file (list 0) field-indexes))


(def column-matchers
  (list (->column-matchers "CustId" 0 =)
        (->column-matchers "Hiring-date" 1 (date-expression "dd-MM-yyyy" "dd/MM/yyyy"))
        (->column-matchers "C2" 2 =)
        (->column-matchers "C3" 3 (int-tolerance-expression 3))))

(deftest simple-rec-test
  (let [row-result (match base-map target-map column-matchers)]
    (pp/pprint row-result)
    (is (= (count row-result) 9))))
