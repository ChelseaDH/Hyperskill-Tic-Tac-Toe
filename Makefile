SRC_DIR := src
OUT_DIR := out
MAIN_CLASS := ch.cdhildit.tictactoe.Main
JAR_FILE := $(OUT_DIR)/tictactoe.jar

SRC_FILES := $(shell find $(SRC_DIR) -name *.java)
CLASS_FILES := $(patsubst $(SRC_DIR)/%,$(OUT_DIR)/%,$(patsubst %.java,%.class,$(SRC_FILES)))

.PHONY: build
build: $(JAR_FILE)

$(JAR_FILE): $(OUT_DIR)/$(subst .,/,$(MAIN_CLASS)).class $(CLASS_FILES)
	jar -cfe $@ $(MAIN_CLASS) -C $(OUT_DIR) .

$(OUT_DIR)/%.class: $(SRC_FILES)
	javac --source-path $(SRC_DIR) -d $(OUT_DIR) $(patsubst $(OUT_DIR)/%,$(SRC_DIR)/%,$(patsubst %.class,%.java,$@))

.PHONY: run
run: $(JAR_FILE)
	@java -jar $(JAR_FILE)

.PHONY: clean
clean:
	$(RM) -r $(OUT_DIR)
