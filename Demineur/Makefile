.DEFAULT_GOAL := allRun
NAME = 'Demineur.jar'
PATHMAIN = 'src/modele/Lanceur.java'
PATHMANIFEST = 'src.modele.Lanceur'
MANIFEST = 'MANIFEST.MF'
BINFOLDER = 'bin/'

all :
	@mkdir -p $(BINFOLDER)
	@javac -d $(BINFOLDER) $(PATHMAIN)
	@echo 'Main-class: '$(PATHMANIFEST) > $(MANIFEST)
	@jar -cvmf $(MANIFEST) $(NAME) -C $(BINFOLDER) ./ >/dev/null

clean :
	@rm -rf $(BINFOLDER) $(MANIFEST)

fclean : clean
	@rm -rf $(NAME)

run :
	@java -jar $(NAME)

allRun : all run
