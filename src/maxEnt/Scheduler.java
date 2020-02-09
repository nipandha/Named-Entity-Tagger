package maxEnt;

import java.io.IOException;

public class Scheduler {
	public static void main(String[] args)
	{
		String[] args1={"/home/user/Documents/Course_Books/NLP/hw6/CONLL_train.pos-chunk-name",
				"/home/user/Documents/Course_Books/NLP/hw6/feature_train.txt"};
		String[] args2={"/home/user/Documents/Course_Books/NLP/hw6/CONLL_test.pos-chunk",
				"/home/user/Documents/Course_Books/NLP/hw6/feature_dev.txt"};
		String[] args3={"/home/user/Documents/Course_Books/NLP/hw6/feature_train.txt",
				"/home/user/Documents/Course_Books/NLP/hw6/model.txt"};
		String[] args4={"/home/user/Documents/Course_Books/NLP/hw6/feature_dev.txt",
				"/home/user/Documents/Course_Books/NLP/hw6/model.txt",
				"/home/user/Documents/Course_Books/NLP/hw6/test_op.txt"};
		try {
			FeatureBuilder.main(args1);
			FeatureBuilder.main(args2);
			MEtrain.main(args3);
			MEtag.main(args4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
}
