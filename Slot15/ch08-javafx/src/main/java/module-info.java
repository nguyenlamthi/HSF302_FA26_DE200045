module fu.de200045.ch08javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;

    opens fu.de200045.ch08javafx to javafx.fxml;
    exports fu.de200045.ch08javafx;

    // Khi làm đến bài nào, chúng ta sẽ mở (uncomment) dòng của bài đó:
     exports fu.de200045.ch08javafx.common;
     exports fu.de200045.ch08javafx.b1;
     exports fu.de200045.ch08javafx.b2;
    // exports fu.de200045.ch08javafx.b3;
    // opens fu.de200045.ch08javafx.b4 to javafx.fxml;
    // exports fu.de200045.ch08javafx.b4;
    // opens fu.de200045.ch08javafx.b5 to javafx.fxml;
    // exports fu.de200045.ch08javafx.b5;
    // opens fu.de200045.ch08javafx.b6 to javafx.fxml;
    // exports fu.de200045.ch08javafx.b6;
    // opens fu.de200045.ch08javafx.t1 to javafx.fxml, javafx.base;
    // exports fu.de200045.ch08javafx.t1;
    // exports fu.de200045.ch08javafx.t2;
    // exports fu.de200045.ch08javafx.t3;
    // opens fu.de200045.ch08javafx.t4 to javafx.fxml, javafx.base;
    // exports fu.de200045.ch08javafx.t4;
    // exports fu.de200045.ch08javafx.t5;
    // exports fu.de200045.ch08javafx.t6;
}